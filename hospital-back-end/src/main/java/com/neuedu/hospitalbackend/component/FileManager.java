package com.neuedu.hospitalbackend.component;

import com.neuedu.hospitalbackend.config.FileManagerConfig;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;

import java.io.File;

/**
 * FastDFS Java客户端工具类
 *
 * @Author: Raven
 * @Date: 2019/6/30 3:36 AM
 */
public class FileManager implements FileManagerConfig {

    private static final long serialVersionUID = 1L;
    private static TrackerClient trackerClient;
    private static TrackerServer trackerServer;
    private static StorageServer storageServer;
    private static StorageClient storageClient;

    static {
        try {
            String classPath = new File(FileManager.class.getResource("/").getFile()).getCanonicalPath();

            String fdfsClientConfigFilePath = classPath + File.separator + CLIENT_CONFIG_FILE;
            ClientGlobal.init(fdfsClientConfigFilePath);

            trackerClient = new TrackerClient();
            trackerServer = trackerClient.getConnection();

            storageClient = new StorageClient(trackerServer, storageServer);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 文件上传
     */
    public static String upload(FastDFSFile file, NameValuePair[] valuePairs) {
        String[] uploadResults = null;
        try {
            uploadResults = storageClient.upload_file(file.getContent(),file.getExt(), valuePairs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String groupName = uploadResults[0];
        String remoteFileName = uploadResults[1];

        String fileAbsolutePath = PROTOCOL
                + TRACKER_NGNIX_ADDR
                //+ trackerServer.getInetSocketAddress().getHostName()
                //+ SEPARATOR + TRACKER_NGNIX_PORT
                + SEPARATOR + groupName
                + SEPARATOR + remoteFileName;
        return fileAbsolutePath;
    }
}