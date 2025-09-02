package com.haiya.videoengine;

import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;

import java.io.IOException;

/**
 * 视频处理引擎SDK
 */
public class VideoEngine {
    
    private final FFmpeg ffmpeg;
    private final FFprobe ffprobe;
    private final FFmpegExecutor ffmpegExecutor;
    
    /**
     * 构造函数，初始化视频处理引擎
     * @throws IOException 如果FFmpeg或FFprobe初始化失败
     */
    public VideoEngine() throws IOException {
        this.ffmpeg = new FFmpeg();
        this.ffprobe = new FFprobe();
        this.ffmpegExecutor = new FFmpegExecutor(ffmpeg, ffprobe);
    }
    
    /**
     * 视频转码方法
     * @param inputPath 输入视频路径
     * @param outputPath 输出视频路径
     * @param format 目标格式
     * @throws IOException 如果转码过程中发生错误
     */
    public void transcode(String inputPath, String outputPath, String format) throws IOException {
        FFmpegBuilder builder = new FFmpegBuilder()
                .setInput(inputPath)
                .overrideOutputFiles(true)
                .addOutput(outputPath)
                .setFormat(format)
                .done();
        
        ffmpegExecutor.createJob(builder).run();
    }
    
    /**
     * 视频压缩方法
     * @param inputPath 输入视频路径
     * @param outputPath 输出视频路径
     * @param crf 压缩质量因子(0-51, 越小质量越高)
     * @throws IOException 如果压缩过程中发生错误
     */
    public void compress(String inputPath, String outputPath, int crf) throws IOException {
        FFmpegBuilder builder = new FFmpegBuilder()
                .setInput(inputPath)
                .overrideOutputFiles(true)
                .addOutput(outputPath)
                .setVideoCodec("libx264")
                .setConstantRateFactor(crf)
                .done();
        
        ffmpegExecutor.createJob(builder).run();
    }
}