package it.geosolutions.imageio.plugins.jp2k;

import java.util.Map;

import javax.management.RuntimeErrorException;

import kdu_jni.KduException;
import kdu_jni.Kdu_codestream;

import java.util.LinkedHashMap;

public class LRUCache {

   private final Map<String,KduNativeWrapper> cache;   

   public LRUCache(final int maxEntries) {
      this.cache = new LinkedHashMap<String,KduNativeWrapper>(maxEntries, 0.75F, true) {
               private static final long serialVersionUID = -1236481390177598762L;
               
               @Override
               protected boolean removeEldestEntry(Map.Entry<String,KduNativeWrapper> eldest){            
                  boolean shouldBeRemoved = size() > maxEntries;
                  
                  if (shouldBeRemoved) {                 	  
            		  KduNativeWrapper wrapper = eldest.getValue();
            		  wrapper.dispose();                  
                  }
                  
                  return shouldBeRemoved;
               }                             
            };
   }

   public void put(String key, KduNativeWrapper value) {
	   cache.put(key, value);
   }
   
   public KduNativeWrapper get(String key) {       
       return cache.get(key);       
   }
}