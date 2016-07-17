;0222

;lazy-seq
(defn lazy-range [start limit]
    (if (<= start limit)
        (cons start (lazy-seq (lazy-range (inc start) limit)))
        nil)
    )

(cons 10 nil) -> (10)