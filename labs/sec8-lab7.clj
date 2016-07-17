;sec8-lab7
;Tianhao Liu
;liux3271

(defn lazy-rand [n]
    (cons (rand-int n) (lazy-seq (lazy-rand n)))
    )

(defn lazy-scale [lst n]
    (if (empty? lst)
        nil
        (cons (* n (first lst)) (lazy-seq (lazy-scale (rest lst) n)))
        )
    )

(defn integers [start]
    (cons start (lazy-seq (integers (inc start)))))

(defn lazy-running-sum [lst]
    (if (empty? (rest lst))
        lst
        (cons
            (first lst)
            (lazy-seq (lazy-running-sum (cons (+ (first lst) (first (rest lst))) (rest (rest lst))))))
        )
    )

(defn lazy-interleave [a b]
    (if (or (empty? a) (empty? b))
        nil
        (lazy-seq
            (cons (first a) (cons (first b) (lazy-interleave (rest a) (rest b))))
            )
        )
    )

(defn lrd [lst s]
    (if (empty? lst)
        nil
        (if (contains? s (first lst))
            (lazy-seq (lrd (rest lst) s))
            (cons (first lst) (lazy-seq (lrd (rest lst) (conj s (first lst)))))
            )
        )
    )

(defn lazy-remove-dup [lst]
    (lrd lst #{})
    )
