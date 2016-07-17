;bonus
;Tianhao Liu
;liux3271

(defn integers [n]
    (lazy-seq (cons n (integers (inc n))))
    )

(defn interl+help [ov1 ov2 v1 v2 finish1 finish2]
    (let 
        [nv1 (if (empty? v1) ov1 v1)
         nv2 (if (empty? v2) ov2 v2)
         f1 (if (empty? v1) 1 finish1)
         f2 (if (empty? v2) 1 finish2)]
        (if (and (not= f1 0) (not= f2 0))
            nil
            (lazy-seq (cons (first nv1) (cons (first nv2) (interl+help ov1 ov2 (rest nv1) (rest nv2) f1 f2))))
            )
        )
    )

(defn interl+ [v1 v2]
    (if (or (empty? v1) (empty? v2))
        '()
        (interl+help v1 v2 v1 v2 0 0)
        )
    )

(def interl+lazy interl+)

(defn in-circle? [x y]
    (if (<= (+ (* x x) (* y y)) 1) 1 0)
    )

(defn generate [nsamples]
    (loop [i nsamples ret 0]
        (if (zero? i)
            ret
            (recur (dec i) (+ ret (in-circle? (rand 1) (rand 1))))
            )
        )
    )

(defn compute-pi [nthreads nsamples]
    (float 
        (* (/ (apply + (pmap (fn [x] (generate nsamples)) (repeat nthreads nil))) (* nthreads nsamples)) 4)
        )
    )