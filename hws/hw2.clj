;hw2
;Tianhao Liu
;liux3271
;Feb. 5

(defn replace-with-sum [l]
    (loop [sum 0 newl [] a l]
        (if (empty? a)
            (if (zero? sum)
                (reverse (into '() newl))
                (reverse (into '() (conj newl sum)))
                )
            (if (number? (first a))
                (recur (+ sum (first a)) newl (rest a))
                (recur sum (conj newl (first a)) (rest a))
                )
            )
        )
    )

(defn running-sum [l]
    (loop [newl [] sum 0 a l]
        (if (empty? a)
            (reverse (into '() newl))
            (recur (conj newl (+ (first a) sum)) (+ (first a) sum) (rest a))
            )
        )
    )

(defn expand [l]
    (loop [newl [] a l]
        (if (empty? a)
            (reverse (into '() newl))
            (if (list? (first a))
                (recur (conj newl (repeat (nth (first a) 0) (nth (first a) 1))) (rest a))
                (recur (conj newl (first a)) (rest a))
                )
            )
        )
    )

(defn factors [l n]
    (loop [a l newl '[]]
        (if (empty? a)
            (reverse (into '() newl))
            (if (= 0 (mod n (first a)))
                (recur (rest a) (conj newl (first a)))
                (recur (rest a) newl)
                )
            )
        )
    )

(defn string->ascii [s]
    (map (fn [x] (- x 32)) (map int s))
    )

(defn ascii-num96 [l]
    (let [n1 (nth l 0) n2 (nth l 1) n3 (nth l 2) n4 (nth l 3) b 96]
        (+ 
            (* n1 (* b b b))
            (* n2 (* b b))
            (* n3 b)
            n4
            )
        )
    )

(defn make-dict [l]
    (loop [cl l a {}]
        (if (empty? cl)
            a
            (recur 
                (rest cl)
                (into a { (first cl) (ascii-num96 (string->ascii (first cl)))})
                )
            )
        )
    )

(defn in-dict [m s]
    (if (contains? m s)
        (ascii-num96 (string->ascii s))
        nil
        )
    )
