0203

(apply + [1 2 3]) = (+ 1 2 3)

(= 'snow 'snow) = true
(identical? 'snow 'snow) = false
(let [x 'snow y x]
    (identical? x y)
    )

Collections:
    list - sequence
    vector - sequence
    map - {:a 1 :b 3}
    set - #{1 3}
Sequence functions:
    first
    rest
    ()
    rseq - reverse
    (rseq [1 2]) -> (2 1)
    (seq?)