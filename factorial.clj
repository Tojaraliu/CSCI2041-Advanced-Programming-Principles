(defn factorial-using-recur [n]
  (loop [current n
         next (dec current)
         total 1]
    (if (> current 1)
      (recur next (dec next) (* total current))
      total)))

(defn factorial-using-reduce [n]
  (reduce * (range 1 (inc n))))

(defn factorial-using-apply-range [n]
  (apply * (range 1 (inc n))))

(defn factorial-using-apply-iterate [n]
  (apply * (take n (iterate inc 1))))

(defn make-fac-function [n]
  (fn [] (reduce * (range 1 (inc n)))))

(defn factorial-using-eval-and-cons [n]
  (eval (cons '* (range 1 (inc n)))))

(defmacro factorial-function-macro [n]
  `(fn [] (* ~@(range 1 (inc n)))))

(defn factorial-using-ref-dosync [n psz]
  (let [result (ref 1)
        parts (partition-all psz (range 1 (inc n)))
        tasks (for [p parts]
      (future
        (Thread/sleep (rand-int 10))
        (dosync (alter result #(apply * % p)))))]
    (dorun (map deref tasks))
    @result))

(defn factorial-using-agent [n psz]
  (let [result (agent 1)
        parts (partition-all psz (range 1 (inc n)))]
    (doseq [p parts]
      (send result #(apply * % p)))
    (await result)
    @result))

(defn factorial-using-pmap-reduce [n psz]
  (let [parts (partition-all psz (range 1 (inc n)))
        sub-factorial-fn #(apply * %)]
    (reduce * (pmap sub-factorial-fn parts))))

(defmacro factorial-using-pvalues-reduce [n psz]
  (let [exprs (for [p (partition-all psz (range 1 (inc n)))]
                (cons '* p))]
    `(fn [] (reduce * (pvalues ~@exprs)))))

(defmacro factorial-using-pcalls-reduce [n psz]
  (let [exprs (for [p (partition-all psz (range 1 (inc n)))]
                `(fn [] (* ~@p)))]
    `(fn [] (reduce * (pcalls ~@exprs)))))

(defn factorial-for-trampoline [n]
  (letfn
    [(next-fac-value [limit current-step previous-value]
       (let [next-value (* current-step previous-value)]
         (if (= limit current-step)
           next-value
           #(next-fac-n limit current-step next-value))))
     (next-fac-n [limit previous-step current-value]
       #(next-fac-value limit (inc previous-step) current-value))]
    (next-fac-value n 1 1)))

(defn factorial-using-atoms-while [n]
  (let [a (atom 0)
        res (atom 1)]
    (while (> n @a)
      (swap! res * (swap! a inc)))
    @res))

(defn factorial-using-do-dotimes [n]
  (do
    (def a 1)
    (dotimes [i n]
      (def a (* a (inc i)))))
  a)

(defn factorial-using-do-while [n]
  (do
    (def a 0)
    (def res 1)
    (while (< a n)
      (def a (inc a))
      (def res (* res a)))
    res))

(defn factorial-using-agent-recursive [n]
  (let [a (agent 1)]
    (letfn [(calc  [current-n limit total]
               (if (< current-n limit)
                 (let [next-n (inc current-n)]
                   (send-off *agent* calc limit (* total next-n))
                   next-n)
                 total))]
      (await (send-off a calc n 1)))
    @a))

(defn consensus [nitems nthreads niters]
  (let [refs (vec (map ref (repeatedly nitems #(rand 1))))]  
       (dosync (ref-set (get refs 0) 0)                      
               (ref-set (get refs (dec nitems) ) (dec nitems)))
       (println (map deref refs))                            
       (dorun
         (pmap (fn [t]                                       
                   (dotimes [j niters]                       
                     (doseq [i (range 1 (dec nitems))]       
                       (dosync                               
                            (alter (get refs i)              
                                          (fn [x] (/ (+  x   
                                              (deref (get refs (dec i)))
                                              (deref (get refs (inc i))))
                                               3. )))))))
                (range nthreads)) )                          
        (map deref refs)))                                   

(defn square-el [v nthreads niters]
  (let [n (count v)
        vels (map atom v)                            
        m (quot (+ nthreads -1 n) nthreads)          
        subvecs (partition-all m vels) ]             
       (apply concat (pmap (fn [part] (double1 part niters)) subvecs))
    )
  )