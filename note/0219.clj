;0219

;lazy sequence

(defn integers [n] 
    (cons (integers (inc n))))

(defn integers [n]
    (cons n (lazy-seq (integers (inc n)))))

(defn fib [a b]
    (cons b (lazy-seq (integers b a+b))))

(def fibs 
    (cons 1 (cons 1 (lazy-seq (map + fibs (rest fibs))))))

(first (integers 10)) -> 10
(second (integers 10)) -> 11
(take 5 (integers 10)) -> (10 11 12 13 14)

(defn lazy-range [n]
    (lazy-range (inc n) limit)
    )