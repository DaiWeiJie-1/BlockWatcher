package com.example.dwj.blockwatcher.outputter;

import android.content.Context;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by dwj on 2016/6/27.
 */
public class FileOutPutter extends AbstractOutputter{

    private Context mContext;
    private String mDirPath;
    private String mFileName;

    public FileOutPutter(String dirPath,String fileName) {
        mFileName = fileName;
        mDirPath = dirPath;
    }

    @Override
    protected void outPutBlockInfo(String blockInfoStr) {
        if (mDirPath == null || mFileName == null) {
            return;
        }

        File dirFile = new File(mDirPath);
        if (!dirFile.exists()) {
            boolean makeDir = dirFile.mkdirs();
            if (makeDir) {
                return;
            }
        }

        File file = new File(dirFile, mFileName);
        if (!file.exists() || !file.isFile()) {
            try {
                boolean fileCreate = file.createNewFile();
                if (!fileCreate) {
                    return;
                }

            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }

        FileWriter fw = null;
        BufferedWriter bw = null;
        try {
            fw = new FileWriter(file, true);
            bw = new BufferedWriter(fw);
            bw.append(blockInfoStr);
            bw.append("\r\n");
            bw.append("\r\n");
            bw.append("\r\n");
            bw.append("\r\n");
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
