;lab4
(defn below? [f th low high]
    (loop [r (range low (+ 1 high))]
        (if (empty? r)
            true
            (if (< th (f (first r)))
                false
                (recur (rest r))
                )
            )
        )
    )
