package com.neuedu.hospitalbackend.config;

import java.io.Serializable;

/**
 * @Author: Raven
 * @Date: 2019/6/30 3:34 AM
 */
public interface FileManagerConfig extends Serializable {
    public static final String FILE_DEFAULT_AUTHOR = "Raven";
    public static final String PROTOCOL = "http://";
    public static final String SEPARATOR = "/";
    public static final String TRACKER_NGNIX_ADDR = "39.108.145.132";
    public static final String TRACKER_NGNIX_PORT = "";
    public static final String CLIENT_CONFIG_FILE = "fastdfs_client.conf";
}