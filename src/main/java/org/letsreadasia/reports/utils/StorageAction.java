package org.letsreadasia.reports.utils;

import com.google.gcloud.storage.Storage;

public abstract class StorageAction<T> {

    abstract void run(Storage storage, T request) throws Exception;

    abstract T parse(String... args) throws Exception;

    protected String params() {
        return "";
    }

}