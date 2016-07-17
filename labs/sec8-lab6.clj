;prob 2

;user=> (fast-prime? 10007)
;true
;user=> (fast-prime? 11007)
;false
;user=> (fast-prime? 1117)
;true
;user=> (fast-prime? 123154117)
;false
;user=> (fast-prime? 12315411)
;false

;prob 3

(defn strongprimes [v]
    (if (empty? v)
        '()
        (if (and (.contains primes1000 (first v))
                 (.contains primes1000 (/ (- (first v) 1) 2)))
            (cons (first v) (strongprimes (rest v)))
            (strongprimes (rest v))
        )
    )
)
