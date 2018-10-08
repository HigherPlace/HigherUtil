package com.bryan.common.utils.image;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.widget.AbsListView;
import android.widget.ImageView;

import com.bryan.common.R;
import com.bryan.common.utils.log.LogUtils;
import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;


/**
 * Created by Administrator on 2016/6/2.
 */
public class ImageBuilder {

    /**
     * 加载图片的模式
     */
    public enum LoadMode {
        /**
         * 网络加载
         */
        URL,
        /**
         * 加载图片文件
         */
        FILE,
        /**
         * 从assets文件夹中加载图片
         */
        ASSETS,
        /**
         * 从drawable文件夹中加载
         */
        DRAWABLE
    }

    // 标识该资源不是图片资源
    public static final int IMAGE_NULL = -1;
    // 默认的全局占位图片
    public static int globalDefaultImgId = R.drawable.pictures_no;
    // 默认全局加载错误占位图片
    public static int globalDefaultErrorImgId = IMAGE_NULL;

    private Context context;
    private Fragment fragment;
    private ImageView iv;
    private String url;
    private int drawableId;
    private boolean isDrawabId; // true,加载drawable里的文件
    private LoadMode loadMode;      // 图片加载模式
    private boolean isCircle;       // 是否要裁切成圆形
    private int radius;         // 圆角矩形的弧度, 如果isCircle为true，则圆角不起作用

    private int defaultImageId = IMAGE_NULL;     // 默认占位图片
    private boolean noDefault;                   // true,不需要默认图片

    private int errorImgId = IMAGE_NULL;
    private boolean noErrorImg;                 // ture，不需要错误图片

    // 指定的宽、高（px）
    private int width;
    private int height;



    private List<Transformation<Bitmap>> transformationList = new ArrayList<>();

    // 图片加载监听
    private RequestListener<String, GlideDrawable> requestListener = new RequestListener<String, GlideDrawable>() {
        @Override
        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
            if (e != null) {
                LogUtils.e("ImageBuilder onException", e.toString() + "  model:" + model + " isFirstResource: " + isFirstResource);
            }
            return false;
        }

        @Override
        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
//            LogUtils.e("ImageBuilder onResourceReady", "isFromMemoryCache:" + isFromMemoryCache + "  model:" + model + " isFirstResource: " + isFirstResource);
            return false;
        }
    };

    private RequestListener<Integer, GlideDrawable> requestListener2 = new RequestListener<Integer, GlideDrawable>() {
        @Override
        public boolean onException(Exception e, Integer model, Target<GlideDrawable> target, boolean isFirstResource) {
            LogUtils.e("ImageBuilder onException", e.toString() + "  model:" + model + " isFirstResource: " + isFirstResource);
            return false;
        }

        @Override
        public boolean onResourceReady(GlideDrawable resource, Integer model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
//            LogUtils.e("ImageBuilder onResourceReady", "isFromMemoryCache:" + isFromMemoryCache + "  model:" + model + " isFirstResource: " + isFirstResource);
            return false;
        }
    };

    /**
     * 图片缩放模式
     */
    private ImageView.ScaleType scaleType = ImageView.ScaleType.CENTER_CROP;
//    /** true,滑动不加载图片 */
//    private boolean isPauseOnSrcoll;

    public ImageBuilder(Context context, ImageView iv, String url) {
        this(context, iv, url, LoadMode.URL);
    }

    public ImageBuilder(Context context, ImageView iv, String url, LoadMode loadMode) {
        this.context = context;
        this.iv = iv;
        this.url = url;
        this.loadMode = loadMode;
    }

    public ImageBuilder(Fragment fragment, ImageView iv, String url, LoadMode loadMode) {
        this.fragment = fragment;
        this.iv = iv;
        this.url = url;
        this.loadMode = loadMode;
    }

    public ImageBuilder(Context context, Fragment fragment, ImageView iv, String url, int drawableId, boolean isDrawabId, LoadMode loadMode, boolean isCircle, int defaultImageId, boolean noDefault, ImageView.ScaleType scaleType) {
        this.context = context;
        this.fragment = fragment;
        this.iv = iv;
        this.url = url;
        this.drawableId = drawableId;
        this.isDrawabId = isDrawabId;
        this.loadMode = loadMode;
        this.isCircle = isCircle;
        this.defaultImageId = defaultImageId;
        this.noDefault = noDefault;
        this.scaleType = scaleType;
    }

    public Context getContext() {
        return context;
    }

    public ImageBuilder setContext(Context context) {
        this.context = context;
        return this;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public ImageBuilder setFragment(Fragment fragment) {
        this.fragment = fragment;
        return this;
    }

    public ImageView getIv() {
        return iv;
    }

    public ImageBuilder setIv(ImageView iv) {
        this.iv = iv;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public ImageBuilder setUrl(String url) {
        this.url = url;
        return this;
    }

    public int getDrawableId() {
        return drawableId;
    }

    public ImageBuilder setDrawableId(int drawableId) {
        this.drawableId = drawableId;
        return this;
    }

    public LoadMode getLoadMode() {
        return loadMode;
    }

    public ImageBuilder setLoadMode(LoadMode loadMode) {
        this.loadMode = loadMode;
        return this;
    }

    public boolean isCircle() {
        return isCircle;
    }

    public ImageBuilder setCircle(boolean circle) {
        isCircle = circle;
        return this;
    }

    public int getDefaultImageId() {
        if (defaultImageId == -1) {
            return globalDefaultImgId;
        }
        return defaultImageId;
    }

    public ImageBuilder setDefaultImageId(int defaultImageId) {
        this.defaultImageId = defaultImageId;
        return this;
    }

    public boolean isNoDefault() {
        return noDefault;
    }

    public ImageBuilder setNoDefault(boolean noDefault) {
        this.noDefault = noDefault;
        return this;
    }

    public ImageView.ScaleType getScaleType() {
        return scaleType;
    }

    public ImageBuilder setScaleType(ImageView.ScaleType scaleType) {
        this.scaleType = scaleType;
        return this;
    }

    public boolean isDrawabId() {
        return isDrawabId;
    }

    public ImageBuilder setDrawabId(boolean drawabId) {
        isDrawabId = drawabId;
        return this;
    }

    public int getErrorImgId() {
        if (errorImgId == IMAGE_NULL) {
            return globalDefaultErrorImgId;
        }
        return errorImgId;
    }

    public ImageBuilder setErrorImgId(int errorImgId) {
        this.errorImgId = errorImgId;
        return this;
    }

    public boolean isNoErrorImg() {
        return noErrorImg;
    }

    public ImageBuilder setNoErrorImg(boolean noErrorImg) {
        this.noErrorImg = noErrorImg;
        return this;
    }

    public RequestListener<String, GlideDrawable> getRequestListener() {
        return requestListener;
    }

    public ImageBuilder setRequestListener(RequestListener<String, GlideDrawable> requestListener) {
        this.requestListener = requestListener;
        return this;
    }

    public int getWidth() {
        return width;
    }

    public ImageBuilder setWidth(int width) {
        this.width = width;
        return this;
    }

    public int getHeight() {
        return height;
    }

    public ImageBuilder setHeight(int height) {
        this.height = height;
        return this;
    }

    public ImageBuilder setSize(int width, int height) {
        this.width = width;
        this.height = height;
        return this;
    }

    public ImageBuilder addBitmapTransform(Transformation<Bitmap> bitmapTransformation) {
        transformationList.add(bitmapTransformation);
        return this;
    }

    public ImageBuilder addBitmapTransform(Transformation<Bitmap> bitmapTransformation, int index) {
        transformationList.add(index, bitmapTransformation);
        return this;
    }

    public List<Transformation<Bitmap>> getTransformationList() {
        return transformationList;
    }

    public int getRadius() {
        return radius;
    }

    public ImageBuilder setRadius(int radius) {
        this.radius = radius;
        return this;
    }

    public RequestListener<Integer, GlideDrawable> getRequestListener2() {
        return requestListener2;
    }

    public ImageBuilder setRequestListener2(RequestListener<Integer, GlideDrawable> requestListener2) {
        this.requestListener2 = requestListener2;
        return this;
    }

    public void build() {
        load(this);
    }

    public static void load(final ImageBuilder imageBuilder) {

        if (imageBuilder.getIv() == null) {
            return;
        }

        RequestManager requestManager = null;
        if (imageBuilder.getContext() != null) {
            if (imageBuilder.getContext() instanceof Activity) {
                Activity activity = (Activity) imageBuilder.getContext();
                requestManager = Glide.with(activity);
            } else {
                requestManager = Glide.with(imageBuilder.getContext());
            }
        } else if (imageBuilder.getFragment() != null) {
            requestManager = Glide.with(imageBuilder.getFragment());
        } else {
            // ImageLoader.getInstance().displayImage(url, iv);
            // TODO 其他情况
            return;
        }

        if (requestManager != null) {
            // TODO
            DrawableTypeRequest drawableTypeRequest = null;

            if (imageBuilder.isDrawabId()) {
                drawableTypeRequest = requestManager.load(imageBuilder.getDrawableId());
            } else {
                if (!TextUtils.isEmpty(imageBuilder.getUrl())) {
                    StringBuilder sbUrl = new StringBuilder();
                    switch (imageBuilder.getLoadMode()) {
                        case URL:

                            break;

                        case FILE:
                            sbUrl.append("file://");
                            break;

                        case ASSETS:
                            sbUrl.append("file:///android_asset");
                            break;

                        case DRAWABLE:
                            break;
                    }
                    sbUrl.append(imageBuilder.getUrl());
                    drawableTypeRequest = requestManager.load(sbUrl.toString());
                } else {
                    imageBuilder.setNoDefault(true);
                    imageBuilder.setDrawabId(true);
                    drawableTypeRequest = requestManager.load(imageBuilder.getDefaultImageId());
                }
            }

            if (drawableTypeRequest != null) {
                if (imageBuilder.isCircle()) {
                    imageBuilder.addBitmapTransform(new CropCircleTransformation(imageBuilder.getContext()));
                } else if (imageBuilder.getRadius() > 0) {
                    imageBuilder.addBitmapTransform(new RoundedCornersTransformation(imageBuilder.getContext(), imageBuilder.getRadius(), 0));
                }

                if (!imageBuilder.isNoDefault() && imageBuilder.getDefaultImageId() != IMAGE_NULL) {
                    drawableTypeRequest.placeholder(imageBuilder.getDefaultImageId());
                }

                if (!imageBuilder.isNoErrorImg() && imageBuilder.getErrorImgId() != IMAGE_NULL) {
                    drawableTypeRequest.error(imageBuilder.getErrorImgId());
                }

                if (imageBuilder.getWidth() > 0 && imageBuilder.getHeight() > 0) {
                    drawableTypeRequest.override(imageBuilder.getWidth(), imageBuilder.getHeight());
                }

                if (imageBuilder.getTransformationList().size() > 0) {
                    // 使用裁切的时候，只有fit_center和center_crop起作用
                    // 注意：必须放在数组第一位
                    if (imageBuilder.getRadius() > 0) {
                        if (imageBuilder.getScaleType() != ImageView.ScaleType.FIT_CENTER) {
                            imageBuilder.addBitmapTransform(new CenterCrop(imageBuilder.getContext()), 0);
                        }
                    } else {
                        imageBuilder.getIv().setScaleType(imageBuilder.getScaleType());
                    }

                    int size = imageBuilder.getTransformationList().size();
                    Transformation<Bitmap>[] transformations = new Transformation[size];
                    for (int i = 0; i < size; i++) {
                        transformations[i] = imageBuilder.getTransformationList().get(i);
                    }

                    drawableTypeRequest.bitmapTransform(transformations);
                } else {
                    imageBuilder.getIv().setScaleType(imageBuilder.getScaleType());
                }

                if (imageBuilder.isDrawabId()) {
                    drawableTypeRequest.listener(imageBuilder.getRequestListener2());
                } else {
                    drawableTypeRequest.listener(imageBuilder.getRequestListener());
                }
                drawableTypeRequest.crossFade()
                        .into(imageBuilder.getIv());
            }
        }
    }

//    public DrawableRequestBuilder<Bitmap> bitmapTransform(List<Transformation<Bitmap>> transformationList) {
//        if(transformationList == null || transformationList.size() == 0) {
//            return null;
//        }
//        GifBitmapWrapperTransformation[] transformations =
//                new GifBitmapWrapperTransformation[transformationList.size()];
//        for (int i = 0; i < transformationList.size(); i++) {
//            transformations[i] = new GifBitmapWrapperTransformation(context, transformationList.get(i));
//        }
//        return transform(transformations);
//    }


    /**
     * 设置滚动不加载图片的监听
     */
    public static void setOnSlidePauseLoadListener(Context context,
                                                   AbsListView absListView) {
        absListView.setOnScrollListener(getOnSlidePauseLoadListener(context));
    }

//    /**
//     * 设置滑动不加载图片的监听
//     *
//     * @param context
//     */
//    public static void setOnSlidePauseLoadListener(final Context context, RecyclerView recyclerView) {
//        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//                setOnSlidePauseLoadImage(context, newState);
//            }
//        });
//    }

    /**
     * 设置滑动不加载图片
     *
     * @param context
     * @param newState
     */
//    public static void setOnSlidePauseLoadImage(Context context, int newState) {
//        switch (newState) {
//            case RecyclerView.SCROLL_STATE_IDLE:
//                Glide.with(context).resumeRequests();
//                break;
//
//            default:
//                Glide.with(context).pauseRequests();
//                break;
//        }
//    }

    /**
     * 获取滚动不加载图片的监听
     *
     * @return
     */
    public static AbsListView.OnScrollListener getOnSlidePauseLoadListener(final Context context) {
        return new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    case SCROLL_STATE_IDLE:
                        Glide.with(context).resumeRequests();
                        break;

                    default:
                        Glide.with(context).pauseRequests();
                        break;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {

            }
        };
    }
}
