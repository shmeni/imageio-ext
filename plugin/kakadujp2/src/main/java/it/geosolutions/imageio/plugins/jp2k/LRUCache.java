   private final Map<K,V> cache;   

   public LRUConcurrentCache(final int maxEntries) {
      this.cache = new LinkedHashMap<K,V>(maxEntries, 0.75F, true) {
               private static final long serialVersionUID = -1236481390177598762L;
               @Override
               protected boolean removeEldestEntry(Map.Entry<K,V> eldest){            
                  return size() > maxEntries;
               }
            };
   }

   public void put(K key, V value) {
       synchronized(cache) {
          cache.put(key, value);
       }
   }
   public V get(K key) {
       synchronized(cache) {
           return cache.get(key);
       }
   }