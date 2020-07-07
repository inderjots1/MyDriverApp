package com.io.my_book_store_track.apiCaller;

import android.app.Activity;

import com.io.my_book_store_track.model.responseModel.LoginAndOrderResponseModel;
import com.io.my_book_store_track.model.responseModel.OrderListResponseModel;
import com.io.my_book_store_track.model.responseModel.OrderUpdateResponseModel;
import com.io.my_book_store_track.utils.UrlLocator;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.async.http.body.FilePart;
import com.koushikdutta.async.http.body.Part;
import com.koushikdutta.ion.Ion;

import java.io.File;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class ApiCaller {
    private static void ssl(Activity activity) {
        Ion.getDefault(activity).getHttpClient().getSSLSocketMiddleware().setTrustManagers(new TrustManager[]{new X509TrustManager() {
            @Override
            public void checkClientTrusted(final X509Certificate[] chain, final String authType) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(final X509Certificate[] chain, final String authType) throws CertificateException {
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        }});
    }
    public static void LoginAndOrderList(Activity activity, String url, String number,
                                         final FutureCallback<LoginAndOrderResponseModel> apiCallBack) {
        ssl(activity);
        JsonObject json = new JsonObject();
        final Gson gson = new Gson();
        Ion.with(activity)
                .load(UrlLocator.getFinalUrl(url, number))
                .noCache().setJsonObjectBody(json)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        LoginAndOrderResponseModel loginAndOrderResponseModel = gson.fromJson(result, LoginAndOrderResponseModel.class);
                        apiCallBack.onCompleted(e, loginAndOrderResponseModel   );
                    }
                });

    }
    public static void OrderList(Activity activity, String url, String number,
                                         final FutureCallback<OrderListResponseModel> apiCallBack) {
        ssl(activity);
        final Gson gson = new Gson();
        Ion.with(activity)
                .load(UrlLocator.getFinalUrl(url, number))
                .noCache()
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        OrderListResponseModel loginAndOrderResponseModel = gson.fromJson(result, OrderListResponseModel.class);
                        apiCallBack.onCompleted(e, loginAndOrderResponseModel   );
                    }
                });

    }


    public static void updateOrder(Activity activity, String url, String orderId,
                                        String driverId, File avatar, String status,
                                      final FutureCallback<OrderUpdateResponseModel> apiCallBack) {
        ssl(activity);
            List<Part> files = new ArrayList();
            files.add(new FilePart("avatar", avatar));
            final Gson gson = new Gson();
            Ion.with(activity)
                    .load(UrlLocator.getFinalUrl(url))
                    .setMultipartParameter("orderId", orderId)
                    .setMultipartParameter("driverId", driverId)
                    .setMultipartParameter("status", status)
                    .addMultipartParts(files)
                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>() {
                        @Override
                        public void onCompleted(Exception e, JsonObject result) {
                            OrderUpdateResponseModel customerRegisterResponseModel = gson.fromJson(result, OrderUpdateResponseModel.class);
                            apiCallBack.onCompleted(e, customerRegisterResponseModel);
                        }
                    });

        }/* else {
            final Gson gson = new Gson();
            List<Part> files = new ArrayList();
            files.add(new FilePart("avatar", avatar));


            Ion.with(activity)
                    .load(UrlLocator.getFinalUrl(url))
                    .setMultipartParameter("orderId", orderId)
                    .setMultipartParameter("driverId", driverId)
                    .addMultipartParts(files)
                    .setMultipartParameter("status", status)

                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>() {
                        @Override
                        public void onCompleted(Exception e, JsonObject result) {
                            OrderUpdateResponseModel customerRegisterResponseModel = gson.fromJson(result, OrderUpdateResponseModel.class);
                            apiCallBack.onCompleted(e, customerRegisterResponseModel);
                        }
                    });

        }*/

}
