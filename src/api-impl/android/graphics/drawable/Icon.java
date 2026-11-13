package android.graphics.drawable;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

public class Icon {
	public static final int TYPE_BITMAP = 1;
	public static final int TYPE_RESOURCE = 2;
	public static final int TYPE_DATA = 3;
	public static final int TYPE_URI = 4;
	public static final int TYPE_ADAPTIVE_BITMAP = 5;

	private final int mType;
	private Bitmap mBitmap;
	private String mResPackage;
	private int mResId;
	private byte[] mDataBytes;
	private int mDataOffset;
	private int mDataLength;
	private String mUriString;

	private Icon(int type) {
		this.mType = type;
	}

	public Icon() {
		this.mType = 0;
	}

	public static Icon createWithResource(Context context, int resId) {
		return createWithResource(context != null ? context.getPackageName() : null, resId);
	}

	public static Icon createWithResource(String resPackage, int resId) {
		Icon icon = new Icon(TYPE_RESOURCE);
		icon.mResPackage = resPackage;
		icon.mResId = resId;
		return icon;
	}

	public static Icon createWithBitmap(Bitmap bitmap) {
		Icon icon = new Icon(TYPE_BITMAP);
		icon.mBitmap = bitmap;
		return icon;
	}

	public static Icon createWithAdaptiveBitmap(Bitmap bitmap) {
		Icon icon = new Icon(TYPE_ADAPTIVE_BITMAP);
		icon.mBitmap = bitmap;
		return icon;
	}

	public static Icon createWithData(byte[] data, int offset, int length) {
		Icon icon = new Icon(TYPE_DATA);
		icon.mDataBytes = data;
		icon.mDataOffset = offset;
		icon.mDataLength = length;
		return icon;
	}

	public static Icon createWithContentUri(String uri) {
		Icon icon = new Icon(TYPE_URI);
		icon.mUriString = uri;
		return icon;
	}

	public static Icon createWithContentUri(Uri uri) {
		return createWithContentUri(uri != null ? uri.toString() : null);
	}

	public int getType() {
		return mType;
	}

	public int getResId() {
		return mResId;
	}

	public String getResPackage() {
		return mResPackage;
	}

	public Drawable loadDrawable(Context context) {
		try {
			switch (mType) {
				case TYPE_BITMAP:
				case TYPE_ADAPTIVE_BITMAP:
					if (mBitmap != null) {
						return context != null
							? new BitmapDrawable(context.getResources(), mBitmap)
							: new BitmapDrawable(mBitmap);
					}
					break;
				case TYPE_RESOURCE:
					if (context != null && context.getResources() != null && mResId != 0) {
						Drawable d = context.getResources().getDrawable(mResId);
						if (d != null) {
							return d;
						}
					}
					break;
				case TYPE_DATA:
					if (mDataBytes != null && mDataLength > 0) {
						Bitmap b = BitmapFactory.decodeByteArray(mDataBytes, mDataOffset, mDataLength);
						if (b != null) {
							return context != null
								? new BitmapDrawable(context.getResources(), b)
								: new BitmapDrawable(b);
						}
					}
					break;
				default:
					break;
			}
		} catch (Exception ignored) {
		}
		return new Drawable();
	}
}
