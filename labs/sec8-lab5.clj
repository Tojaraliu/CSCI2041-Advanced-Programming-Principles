;sec8-lab5
;Tianhao Liu
;liux3271

(defn num-digits [n]
    (count (str n))
)

(defn gcd [a b]
    (if (= b 0)
        a
        (recur b (mod a b))
    )
)

(defn to-dec [l base]
    (loop [nl (reverse l) num 0 sum 0]
        (if (empty? nl)
            sum
            (recur (rest nl)
                   (inc num)
                   (+ sum (* (apply * (repeat num base)) (first nl)) ))
        )
    )
)

(defn from-dec [n base]
    (loop [ret '() num n]
        (if (zero? num)
            ret
            (recur (conj ret (mod num base)) (quot num base))
        )
    )
)
