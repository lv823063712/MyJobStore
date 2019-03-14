package soexample.bigfly.com.myjob_store.utils.appli;

import android.app.Application;

import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;

/**
 * <p>文件描述：<p>
 * <p>作者：${吕飞}<p>
 * <p>创建时间：2019/2/25   14:12<p>
 * <p>更改时间：2019/2/25   14:12<p>
 * <p>版本号：1<p>
 */

public class appli extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //Fresco的初始化

        //磁盘缓存的配置
        DiskCacheConfig diskCacheConfig = DiskCacheConfig.newBuilder(this)
                .setBaseDirectoryPath(getCacheDir())
                .setMaxCacheSize(8*1024*1024)
                .build();
        //把磁盘缓存的设置，设置到三级缓存中
        ImagePipelineConfig pipelineConfig = ImagePipelineConfig.newBuilder(this)
                .setMainDiskCacheConfig(diskCacheConfig)
                .build();
        Fresco.initialize(this,pipelineConfig);
    }

}
