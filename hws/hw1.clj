;Tianhao Liu
;CSci homework#1
;01/26/2016

(:import java.lang.Math)

(defn maxmul7 [a b]
    (loop [i b]
        (if (and (not (= (mod i 7) 0)) (>= i a) )
            (recur (- i 1))
            (cond
                (= i (- a 1)) nil
                :else i)
            )
        )
    )

(defn sumcube [a b]
    (loop [sum 0 i a]
        (if (<= i b)
            (recur (+ sum (* i i i)) (+ i 1))
            sum
            )
        )
    )

(defn drop2nd [a]
    (loop [b [] i 0]
        (if (< i (count a))
            (recur
                (if (not (= i 1)) (conj b (get a i)) b)
                (+ i 1)
                )
            b
            )
        )
    )

(defn perfect [n]
    (loop [sum 0 i 1]
        (if (< i n)
            (recur
                (if (= (mod n i) 0)
                    (+ sum i)
                    sum)
                (+ i 1)
                )
            (= sum i)
            )
        )
    )

(defn ave [n]
    (loop [sum 0 i 0]
        (if (< i (count n))
            (recur
                (+ sum (get n i))
                (+ i 1)
                )
            (/ sum (count n))
            )
        )
    )

(defn stddev [n]
    (loop [sum 0.0 i 0 a (ave n)]
        (if (< i (count n))
            (recur
                (+ sum (* (- (get n i) a) (- (get n i) a)) )
                (+ i 1)
                a
                )
            (Math/sqrt (/ sum (- (count n) 1)))
            )
        )
    )

(defn posel [n target]
    (loop [i 0 l (count n)]
        (cond
            (= i l) nil
            (= (get n i) target) i
            :else (recur (+ i 1) l)
            )
        )
    )
