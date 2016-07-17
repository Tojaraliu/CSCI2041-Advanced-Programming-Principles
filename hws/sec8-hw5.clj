;sec8-hw5
;Tianhao Liu
;liux3271
;03/26

;Init
(def scoreboard 
    (hash-map 
         :cc 3
         :cd 0
         :dc 5
         :dd 1
         )
    )

(defn score [s1 s2]
    (get scoreboard (keyword (str s1 s2)))
    )

;Strategies
(defn all-defect-strategy [his op-his] 'd)

(defn all-cooperate-strategy [his op-his] 'c)

(defn random-strategy [his op-his] 
    (rand-nth (list 'c 'd))
    )

(defn tit-for-tat-strategy [his op-his]
    (if (empty? op-his)
        'c
        (last op-his))
    )

(defn tit-for-two-tats-strategy [his op-his]
    (let [l (count his)]
        (if (< l 2)
            'c
            (let [l1 (get op-his (- l 1)) l2 (get op-his (- l 2))]
                (cond 
                    (and (= l1 'c) (= l2 'c)) 'd
                    (and (= l1 'd) (= l2 'd)) 'c
                    :else 'c
                    )
                )
            )
        )
    )

;Competition
(defn compete-n-times [strg1 strg2 n]
    (let [a-history (atom []) b-history (atom [])]
        (reset! a-history [])
        (reset! b-history [])
        (loop [i n a-score 0 b-score 0]
            (if (> i 0)
                (do
                    (swap! a-history conj 
                        (strg1 @a-history @b-history)
                        )
                    (swap! b-history conj 
                        (strg2 b-history 
                            (take (- (count @a-history) 1) @a-history))
                            ;since it already one step forward
                        )
                    (recur 
                        (dec i) 
                        (+ a-score 
                            (score (last @a-history) (last @b-history))
                            )
                        (+ b-score 
                            (score (last @b-history) (last @a-history))
                            )
                        )
                    )
                [(list 'Player1-score a-score) (list 'Player2-score b-score)]
                )
            )
        )
    )