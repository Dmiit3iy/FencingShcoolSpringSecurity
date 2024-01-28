package org.dmiit3iy.retrofit;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import org.dmiit3iy.dto.ResponseResult;
import org.dmiit3iy.model.Trainer;
import org.dmiit3iy.model.User;
import org.dmiit3iy.util.Constants;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import retrofit2.http.Header;

import java.io.IOException;
import java.util.List;
import java.util.prefs.Preferences;

public class TrainerRepository {
    private ObjectMapper objectMapper;
    private TrainerService service;


    public TrainerRepository(String username,String password) {

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new BasicAuthInterceptor(username, password)).build();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.URL + "trainer/")
                .addConverterFactory(JacksonConverterFactory.create(objectMapper)).client(client).build();

        this.service = retrofit.create(TrainerService.class);
    }

    private <T> T getData(Response<ResponseResult<T>> execute) throws IOException {
        if (execute.code() != 200) {
            String string = execute.errorBody().string();
            System.out.println(string);
            String message = objectMapper.readValue(string,
                    new TypeReference<ResponseResult<T>>() {
                    }).getMessage();
            System.out.println(message);
            throw new IllegalArgumentException(message);
        }
        return execute.body().getData();
    }



    public Trainer post(Trainer trainer) throws IOException {
        Response<ResponseResult<Trainer>> execute = this.service.post(trainer).execute();
        return getData(execute);
    }

    public Trainer put(Trainer trainer) throws IOException {
        Response<ResponseResult<Trainer>> execute = this.service.put(trainer).execute();
        return getData(execute);
    }


    public Trainer getById(long id) throws IOException {
        Response<ResponseResult<Trainer>> execute = service.getById(id).execute();
        return getData(execute);
    }

    public List<Trainer> get() throws IOException {
        Response<ResponseResult<List<Trainer>>> execute = service.getAll().execute();
        System.out.println(execute);
        return getData(execute);
    }

    public Trainer delete(long id) throws IOException {
        Response<ResponseResult<Trainer>> execute = service.delete(id).execute();
        return getData(execute);
    }

}
