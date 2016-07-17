(defn prime? [n]
    (loop [i 2 limit (int (Math/sqrt n))]
        (if (> i limit)
            true
            (if (= (mod n i) 0)
                false
                (recur (+ i 1) limit)
                )
            )
        )
    )

(defn sum-primes [l r]
    (loop [sum 0 i l]
        (if (> i r)
            sum
            (if (prime? i)
                (recur (+ sum i) (+ i 1))
                (recur sum (+ i 1))
                )
            )
        )
    )

(defn sum-even [l r]
    (loop [sum 0 i l]
        (if (> i r)
            sum
            (if (even? i)
                (recur (+ sum i) (+ i 1))
                (recur sum (+ i 1))
                )
            )
        )
    )

(defn square [a] (* a a))

(defn cube [a] (* a a a))

(defn sum [func l r]
    (loop [sum 0 i l]
        (if (> i r)
            sum
            (if (func i)
                (recur (+ sum i) (+ i 1))
                (recur sum (+ i 1))
                )
            )
        )
    )

(defn sum2 [func l r]
    (loop [sum 0 i l]
        (if (> i r)
            sum
            (recur (+ sum (func i)) (+ i 1))
            )
        )
    )

(defn sum3 [func1 func2 l r]
    (loop [sum 0 i l]
        (if (> i r)
            sum
            (if (func2 i)
                (recur (+ sum (func1 i)) (+ i 1))
                (recur sum (+ i 1))
                )
            )
        )
    )
