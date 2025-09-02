package com.haiya.effects;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

/**
 * 特效处理引擎SDK
 */
public class EffectsEngine {
    
    static {
        // 加载OpenCV库
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }
    
    /**
     * 应用美颜效果
     * @param src 原始图像
     * @return 处理后的图像
     */
    public Mat applyBeautyEffect(Mat src) {
        Mat dst = new Mat();
        // 双边滤波实现美颜效果
        Imgproc.bilateralFilter(src, dst, 15, 80, 80);
        return dst;
    }
    
    /**
     * 应用黑白效果
     * @param src 原始图像
     * @return 处理后的图像
     */
    public Mat applyBlackAndWhiteEffect(Mat src) {
        Mat dst = new Mat();
        // 转换为灰度图像
        Imgproc.cvtColor(src, dst, Imgproc.COLOR_BGR2GRAY);
        return dst;
    }
    
    /**
     * 应用模糊效果
     * @param src 原始图像
     * @param kernelSize 模糊核大小
     * @return 处理后的图像
     */
    public Mat applyBlurEffect(Mat src, int kernelSize) {
        Mat dst = new Mat();
        // 高斯模糊
        Imgproc.GaussianBlur(src, dst, new org.opencv.core.Size(kernelSize, kernelSize), 0);
        return dst;
    }
}