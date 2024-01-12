package com.photo.auth.security;

import org.springframework.boot.configurationprocessor.json.*;

public class JsonTest {

    String nama;
    String alamat;
    String jabatan;

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getJabatan() {
        return jabatan;
    }

    public void setJabatan(String jabatan) {
        this.jabatan = jabatan;
    }

    @Override
    public String toString() {
        return "Json{" +
                "nama='" + nama + '\'' +
                ", alamat='" + alamat + '\'' +
                ", jabatan='" + jabatan + '\'' +
                '}';
    }


    public void convert() {
        String string = "[{Nama:BoniX-1,Alamat:Jakarta-1, Jabatan:Manager-1}, {Nama:BoniX-2, Alamat:Jakarta-2, Jabatan:Manager-2}]";

        try {
            JSONArray jsonArray = new JSONArray(string);

            JsonTest[] jsonClass = new JsonTest[string.length()];

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonObject = jsonArray.getJSONObject(i);

                JsonTest jsonNewObject = new JsonTest();
                jsonNewObject.setNama(jsonObject.getString("Nama"));
                jsonNewObject.setAlamat(jsonObject.getString("Alamat"));
                jsonNewObject.setJabatan(jsonObject.getString("Jabatan"));

                jsonClass[i] = jsonNewObject;

                System.out.println("objek" + jsonObject);
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

    }
    public static void main(String[] args) {

        JsonTest jsonTest = new JsonTest();
        jsonTest.convert();
    }
}
