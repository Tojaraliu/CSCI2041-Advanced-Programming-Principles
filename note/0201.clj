0201

Title and section number as the file name for submitting

(defn divisible [p]
    (fn [n] 
        (= 0 (mod n p))
        )
    )

((divisible 3) 6)

(def divby3 (divisible 3))
(def divby4 (divisible 4))
(divby3 10) -> false

(def divby3
    #(= 0 (mod %1 3))
    )

(def makelist2
    #(list %1 %2)
    )

(def makelist2+
    #(list %1 %2 %&)
    )

(defn second [v]
    (first (rest v))
    )

(def fourth
    (comp first rest rest rest) ;(first (rest (rest (rest ...))))
    )