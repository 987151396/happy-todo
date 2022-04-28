/*
package com.ifreegroup.app.ebbly.lib_common.service;

import com.ifreegroup.app.ebbly.lib_common.utils.AppLogUtil;
import com.squareup.leakcanary.AnalysisResult;
import com.squareup.leakcanary.DisplayLeakService;
import com.squareup.leakcanary.HeapDump;

public class LeakCanaryService extends DisplayLeakService {

    private final String TAG = "LeakCanaryService";

    @Override
    protected void afterDefaultHandling(HeapDump heapDump, AnalysisResult result, String leakInfo) {
        super.afterDefaultHandling(heapDump, result, leakInfo);
        AppLogUtil.d(TAG,"AnalysisResult:" + result.toString()+"\r\n");
        AppLogUtil.d(TAG,"afterDefaultHandling:" + leakInfo);
    }
}
*/
