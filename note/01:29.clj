;01/29
(time (perfect xxx)) -> Efficiency
(fact 100000N) -> 100000 becomes long integer

(reduce + [1 2 3]);+ takes 2 args
-> (+ (+ 1 2) 3) ;left associate
-> (+ 1 (+ 2 3)) ;right associate

scalar production of 2 vectors
[1 2] [3 4] -> (1*3)+(2*4) = 11
(reduce + (map * v1 v2))

(apply  +     [1 2 3]) -> (+ 1 2 3)
       (fn)  (seq. of args to function) 

closure

;(def a 10)
;(defn foo [x] (+ a x))

(let [a 10]
    (defn foo[x] (+ a x)))

function that returns a function
(defn divisible [m]
    (fn [num] 
        (= 0 (mod num m))
        )
    )
((divisible 3) 6)