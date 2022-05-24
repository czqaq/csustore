package org.csu.store.service;

import org.csu.store.common.CommonResponse;

import java.io.InputStream;

public interface FileService {

    CommonResponse<String> saveFile(InputStream inputStream, String fileName);
}
