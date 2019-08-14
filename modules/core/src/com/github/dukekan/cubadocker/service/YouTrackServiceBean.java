package com.github.dukekan.cubadocker.service;

import com.github.dukekan.cubadocker.util.ScalarService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.ResponseBody;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.HttpException;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * @author d.kuznetsov
 * @version $Id$
 */
@Service(YouTrackService.NAME)
public class YouTrackServiceBean implements YouTrackService {

    private Logger logger = LoggerFactory.getLogger(YouTrackServiceBean.NAME);

    @Override
    public List<String> getVerifiedIssueNumbers() throws IOException {
        ScalarService client = createClient();
        Call<ResponseBody> stringCall = client.getStringResponse(
                "admin/projects/60-233/issues?$top=5000&query=%23Verified&fields=numberInProject,fields(projectCustomField(field(name)),value(name))");
        Response<ResponseBody> response = stringCall.execute();

        validateResponse(response);
        String text = requireNonNull(response.body()).string();

        return parseIssueNumbers(text);
    }

    private List<String> parseIssueNumbers(String text) {
        JSONArray issues = new JSONArray(text);
        List<String> resolvedNumbers = new ArrayList<>();
        for (int i = 0; i < issues.length(); i++) {
            JSONObject issue = (JSONObject) issues.get(i);
            Integer number = (Integer) issue.get("numberInProject");
            JSONArray customFields = (JSONArray) issue.get("fields");
            boolean stateIsFixed = false;
            for (int j = 0; j < customFields.length(); j++) {
                JSONObject customField = (JSONObject) customFields.get(j);
                if (!customField.isNull("value") && customField.optJSONObject("value") != null) {
                    JSONObject value = (JSONObject) customField.get("value");
                    if (value.opt("name") != null && "Verified".equals(value.get("name"))) {
                        stateIsFixed = true;
                        break;
                    }
                }
            }
            if (stateIsFixed) {
                resolvedNumbers.add(String.valueOf(number));
            }
        }
        return resolvedNumbers;
    }

    private ScalarService createClient() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://youtrack.haulmont.com/api/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return retrofit.create(ScalarService.class);
    }

    private void validateResponse(Response<ResponseBody> execute) {
        if (execute.code() != 200) {
            logger.error("Non 200 code from youtrack: " + execute.code() + ", body: " +
                    execute.body() + ", error body: " + execute.errorBody());
            throw new HttpException(execute);
        }
        if (execute.body() == null) {
            logger.error("Empty response body from youtrack: " + execute.code() + ", body: " +
                    execute.body() + ", error body: " + execute.errorBody());
            throw new HttpException(execute);
        }
    }
}
