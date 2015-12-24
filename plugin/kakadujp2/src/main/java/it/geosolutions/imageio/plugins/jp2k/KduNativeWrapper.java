package it.geosolutions.imageio.plugins.jp2k;

import com.sun.media.jfxmediaimpl.MediaDisposer.Disposable;

import kdu_jni.Jp2_family_src;
import kdu_jni.Jpx_source;
import kdu_jni.KduException;
import kdu_jni.Kdu_codestream;
import kdu_jni.Kdu_simple_file_source;

public class KduNativeWrapper implements Disposable {
	private Kdu_codestream codestream;		

	private Kdu_simple_file_source localRawSource;
	private Jp2_family_src localFamilySource;
	private Jpx_source localWrappedSource;
	private boolean isRawSource;
	private boolean isDisposed;
    
    public KduNativeWrapper(Kdu_codestream codestream, 
    						boolean isRawSource,
    						Kdu_simple_file_source localRawSource,
    						Jp2_family_src localFamilySource,
    						Jpx_source localWrappedSource) {
    	this.isDisposed = false;
    	
    	this.codestream = codestream;
    	this.isRawSource = isRawSource;
    	this.localRawSource = localRawSource;
    	this.localFamilySource = localFamilySource;
    	this.localWrappedSource = localWrappedSource;
    }
    
    public Kdu_codestream getCodestream() {
		return codestream;
	}    

	@Override
	public void dispose() {
		
		if (this.isDisposed) {
			return;
		}
		
		if (this.codestream != null) {
			try {
				this.codestream.Destroy();
				this.codestream = null;
			} catch (KduException e) {
				throw new RuntimeException(
	                    "Error caused by a Kakadu exception during destruction of codestream: ", e);
			}				
		}
		
		if (!this.isRawSource) {
            try {
				if (this.localWrappedSource.Exists())
					this.localWrappedSource.Close();
			} catch (KduException e) {
			}
            
            this.localWrappedSource.Native_destroy();
            try {
				if (this.localFamilySource.Exists())
					this.localFamilySource.Close();
			} catch (KduException e) {
			}
            
            this.localFamilySource.Native_destroy();

        } else {
        	this.localRawSource.Native_destroy();
        }       
        
        this.isDisposed = true;
	}
}
