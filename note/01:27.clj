(defn foo1 [x]
     (let [b 3]
     (+ x b)
     )
   )

(let [b 4]
     (println b)
     (let [b 100]
          (println b)
          )
     )

(let [b 4]
     (println b)
     (let [a 100]
          (+ a b))
     (* 2 b)
)

(sumdowneven 1000)
(defn sumdowneven [n]
     (if (= n 0)
          0
          (if (even? n)
               (+ n (sumdowneven (dec n))) ;not tail recursive
               (sumdowneven (dec n))
               )
          )
     )

(defn sumdowneven [n rs]
     (if (= n 0)
          rs
          (if (even? n)
               (recur (dec n) (+ rs n)) ;tail recursive
               (recur (dec n) rs)
               )
          )
     )

(defn sumdown [n]
     (sumdowneven n 0))