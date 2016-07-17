;0224

(defn integers [start]
    (println "start=" start)
    (cons start (lazy-seq (integers (inc start)))))

(def ints (integers 1000))
(take 100 ints) ;print
(take 50 ints) ;no print
(take 150 ints) ;print rest 50 elements

(iterate inc 5) -> (5 6 7 8 9 ...) ;lazy sequence

(def fib (map first (iterate (fn [[a b]] [b (+' a b)]) [0 1])))
(take 10 fib) -> (0 1 1 2 3 5 8 13 21 34)

(require 'clojure.pprint)

(defn ccount [v]
    (map (fn [x] (count (filter (fn [y] (= y x)) v) )) v)
    )

;0226

RSA - p,q,d,e
public: n,e
private: n,p,q
p, q: large prime + strong prime [(p-1)/2 and (q-1)/2] are prime too
n = p * q
m = (p-1) * (q-1)
e: 1 < e < n
    e,n: relatively prime gcd(e,n) == 1
d: modular multiplicative inverse of e
    (d*e) mod m == 1
(m^e)^d mod n = m

encrypt
    plain txt = 1 word
    (word->number "hello") = NNNN
    (NNNN)^e mod n -> cyphertxt

decrypt
    (cyphertxt)^d mod n -> NNNN

"lisp lovers get garbage collected"
(partition-by (fn [x] (= x \space)) "lisp lovers get garbage collected")
-> ((\l \i \s \p) (\space) (\l \o \v \e \r \s) (\space) (\g \e \t) (\space) (\g \a \r \b \a \g \e) (\space) (\c \o \l \l \e \c \t \e \d))
=> (number number ... number)
=> (crphernumber crphernumber ... crphernumber)

;0229
;constructor for public key: n (modular), e (exp)
(defn make-public-key [n e]
    {:mod n :exp e})
(defn make-public-key [n e]
    (list n e))
;Selector
(defn public-key-mod [pk]
    (get pk :mod))
(defn public-key-mod [pk]
    (first pk))
(def public-key (make-public-key n e))

(def s "hello i am here")
(seq s) -> (\h \e \l \l \o \w \o \r \l \d)
(map int (seq s)) -> (104 101 108 108 111 119 111 114 108 100)
(apply str (map char (map int s))) -> "helloworld"

(rest [1]) -> ()
(next [1]) -> () ; = (seq (rest [1]))
(seq '()) -> nil

(seq? '()) -> true
(seq? []) -> false

;0304
(defmacro double[x] (+ 2 x))
(macroexpand `(double 6)) -> 12
(defmacro double[x] `(* 2 ~x)) ;symbolic quote
~: evaluate x

(when <test> expr1 expr2 ...) -> nil
(macroexpand '(when 1 2 3 4)) -> (if 1 (do 2 3 4))
(when (even? a) (print "get it") (+ a 2)) -> nil
=> (if (even? a) (do (print "get it") (+ a 2)))

(do ) -> last expr
(doseq) -> nil

(defmacro when [test & body] `(if ~test (do ~@body)))

(defmacro unless [test & body]

;03/07
(defmacro define-x [] `(do (def x 2) (list x)))
(def x 100)
(define-x) -> '(2)

(let [a (gensym)
      c (gensym "g-")
      b#]
      `(let [~a 3 ~c 10]
            (print ~a ~c)))

(defmacro define-x []
    (let [sym (gensym)]
        `(do (def ~sym 2)
             (list ~sym)
             )
        )
    )

(define-x) -> '(2)

;0309

(bigint )

(ntimes n & body)

(defmacro ntimes [n & body]
    `(loop [counter# ~n]
        (when (> counter# 0)
            ~@body
            (recur (dec counter#))
            )
        )
    )

(defmacro ntimes2 [n & body]
    (let [counter (gensym)]
        `(loop [~counter ~n]
            (when (> ~counter# 0)
                ~@body
                (recur (dec ~counter))
                )
            )
        )
    )

;0311
'a -> a ;symbol
`a -> user/a ;qualified symbol

(defmacro resolution [] `x)
(def x 9)
(let [x 100] (resolution)) -> 9

(ns ch1) ;ch1 is the name space -no quote
(in-ns 'ch1)
(create-ns 'ch1)

(intern 'ch2 'a "6666")
(ns-interns 'ch1)
*ns* -> #<Namespace ch1>

(ns foo
    (:require clojure.set)
    (:require [clojure.set :as s])
    )

;0321
read
    immutable data
    mutable data

online travel booking
    everyone can read concurrently
        values can be different if data change
    who can write?

software transaction memory (STM)
    transaction fails
        keeps log of the steps
        undo changes when fail
        retry
            steps need to be "undoable"
            "retriable"
    asynchronous or synchronous
    coordinated or uncoordinated

4 reference types
    Atom  - uncoordinated, synchronous, retriable
    Agent - uncoordinated, asynchronous
    Var   - thread safe
    Ref   - coordinated, synchronous, retriable

Atom
(def x (atom 0))
(deref x) -> 0
(swap! x inc)
@x -> 1
(let [num (atom 0) s1 @num]
    (swap! num inc)
    (println "old value: " s1)
    (println "new value: " @num)
    )

Agent
    (def a (agent 1))
    (send a inc)

;03/23
(def x (atom 10))
@x
(reset! x 20)
(swap! x inc) => (inc @x)
(swap! x + 1) => (+ @x 1)
(swap! x (fn [a b] (+ a b)) 10)
(swap! x (fn [a] (/ 10 a)))

(def bankacc (atom {:balance 0 :log []}))
@bankacc -> {:balance 0 :log []}
(swap! bankacc
    (fn [a m]
        (assoc a :balance
            (+ m (get a :balance)))
        )
    10
    )

;03/25
(def a (agent 0))

(send a inc)
;do other things w/o a
(await a)

(deref a)
@a
(send a + 10)
(restart-agent a 0)

VAR
    def defn defmacro
    - static variable 
    - immutable

(def ^:dynamic y 1)
(binding [x 10] 
    (set! x (+ 2 x))
    (println x)
    x
    )
(var-get x)
(var-set x val)
(set! x val) usable only in binding
(with-local-vars [a 1]
    (var-set a (+ 3 a)))

;03/28
transient
persistent
http://clojure.org/reference/transients
lists cannot be transient

(defn vrange [n]
    (loop [i 0 v []]
        (if (< i n)
            (recur (inc i) (conj v i))
            v
            )
        )
    )

(defn vrange2 [n]
    (loop [i 0 v (transient [])]
        (if (< i n)
            (recur (inc i) (conj! v i))
            (persistent! v)
            )
        )
    )

REF
(def x (ref 1))
(dosync <operations>)
(ref-set ref value)
(alter ref func)
(commute ref func)

(dosync (alter x inc))
(dosync (alter x inc)
        (print "hello")
        ...)

2 bank accounts with some money
(def a1 (ref 10))
(def a2 (ref 10))
(defn transfer [x1 x2 amount]
    (dosync 
        (alter x1 - amount)
        (alter x2 + amount)
        )
    (println "balance1 " @x1)
    (println "balance2 " @x2)
    )

;03/30
REF
(def myaccount (ref 10))
(def youraccount (ref 0))
(defn transfer [a1 a2 amount]
    (dosync
        (if (<= amount @a1)
            (do 
                (alter a1 - amount)
                (alter a2 + amount)
                )
            (println "insufficient")
            )
        )
    )

(io! (println "..."))

(def *bankaccount* ...)

(defn total [& accounts]
    (reduce + (map deref accounts))
    )

;04/01

Check specific symbols
(defmacro my-if [cn then expr & args]
    (if (= then 'then) )
    )

(defmacro foo []
    `(let [x# 10]
        (println x#)))

(ntimes 10 (println "aaa"))

(defmacro ntimes [n & body]
    `(loop [i# 0]
        (when (< i# ~n)
            ~@body
            (recur (inc i#))
            )
        )
    )

(for [i 10] (print i))

(defmacro for [[v n] & body]
    `(let [f# ~n]
        (loop [~v 0]
            (when (< ~v f#)
                ~@body
                (recur (inc ~v))
                )
            )
        )
    )

(defmacro p [x]
    `(print '~x))

(defmacro dt [x end & body]
    `(loop [~x 0 lst# []]
        (if (< ~x ~end)
            (let [ret# (list ~@body)]
                (recur (inc ~x) (conj lst# (last ret#)))
                )
            (seq lst#)
            )
        )
    )

;楠总
(defmacro dt-l [x end & body]
    (let [rs (gensym)]
        `(loop [~x 0 ~rs []]
            (if (< ~x ~end)
                (recur (inc ~x) (conj ~rs (do ~@body)))
                ~rs))))

;0406
regular expression
#""
(re-seq #"\w+" "hello how are you") -> ("hello" "how" "are" "you")
(re-seq #"\w" "hello how are you") -> ("h" "e" "l" "l" "o" "h" "o" "w" "a" "r" "e" "y" "o" "u")
\w: word
\d: digit
\s: space

(re-seq #"\d{3}-\d{2}-\d{4}" "123-45-6789")
(re-seq #"\d*") ;0 or more
(re-seq #"\d?") ;once or not
(re-seq #"\d{1,3}") ;at least 1, at most 3
(re-seq #"\d{1,}") ;at least 1

#"[abc]" ;a, b, or c ;alternatives
#"[0-5]" ;0-5
#"[a-z]"
#"[A-Z]"
#"[a-zA-Z]"
#"^[0-5]" ;^ beginning of the line
#"$[0-5]" ;$ end of the line
(re-seq #"\d{1,2}/\d{1,2}/\d{4}" "2/2/2013")
(re-seq #"Apr|May|Jun|\d{1,2}/\d{1,2}/\d{4}" "02/02/2013")
#"[^0-5]" ;^ = not
#"[\d{2}.\d{3}]" ; . = any character

;04/08
#"colou?r" -> "colour" or "color"
#"(?:cat|dog)food"
(clojure.string/replace "string" #"rep" "replacement")
(re-seq #"the (\w+t)" "the cat sat on the mat") -> (["the cat" "cat"] ["the mat" "mat"])
(re-seq #"[A-Z]\w[A-Z]?\w" "John Doe")

;04/11

re-seq returns a list
re-find returns only 1st match
        -> a string
        -> or a vector if any groups
re-matches returns value if any simple match
            -> a string
            -> or a vector if any groups

(re-seq #"\w+\s\w+" "hello how are you") -> ("hello how" "are you")
(re-matches #"\w+\s\w+" "hello how are you") -> nil
Greedy:
(re-seq #"\d?" "3ab45jj6") -> ("3" "" "" "4" "5" "" "" "6")
Reluctant:
(re-seq #"\d+?" "3ab45jj6") -> ("3" "4" "5" "6")
Reluctant:
(re-seq #"\d*?" "3ab45jj6") -> ("" "" "" "" "" "" "" "")

Reluctant:
(re-seq #"\d{1,3}?" "123456789") -> ("1" "2" "3" "4" "5" "6" "7" "8" "9")
user=> (re-seq #"\d{1,}?" "123456789") -> ("1" "2" "3" "4" "5" "6" "7" "8" "9")
Greedy:
(re-seq #"\d{1,3}" "123456789") -> ("123" "456" "789")
(re-seq #"\d{1,}" "123456789") -> ("123456789")

(re-seq #"(a(b)?)" "abaaabb") -> 
(["ab" "ab" "b"] ["a" "a" nil] ["a" "a" nil] ["ab" "ab" "b"])
(re-seq #"(a(b)+?)" "abaaabb") ->
(["ab" "ab" "b"] ["ab" "ab" "b"])
(re-seq #"(a(b)*?)" "abaaabb") ->
(["a" "a" nil] ["a" "a" nil] ["a" "a" nil] ["a" "a" nil])

(with-local-vars [acc "a" balance 10]
    (println "acc " (var-get acc) " balance "
        @balance
        )
    )

;04/15
pvalues
Returns a lazy sequence of the values of the exprs, which are
evaluated in parallel

pvalues is a macro
(pvalues (+ 3 5))

pcalls
Executes the no-arg fns in parallel, returning a lazy sequence of
their values

(def actions [#(sleeping 100 "ha") #(sleeping 200 "he")])
(defn sleeping [n what] (Thread/sleep n) what)

#(sleeping 100 "ha") = (fn [] (sleeping 10 "ha"))
change a function to a no-args function

pmap
Like map, except f is applied in parallel.
(pmap fib [20 55 33])
(reduce + (pmap fib [20 55 33]))

;04/18

(def a (promise))
(pcalls #(deliver a 80)
        #(deliver b 1000))
(pcalls #(do (prn b) (deliver a 80))
        #(do (prn a) (deliver b 1000)))
(send agent fn) -> send to a thread from pool of threads
(send-off agent fn) -> get a new thread

;04/22
(try <exp>
    (catch ...)
    (finally ...)
    )
(throw expr)

(try (/ 1 0)
    (catch ArithmeticException e (println "catched:" e))
    (finally (println "hurra"))
    )

(defn tc [f]
    (try (eval f)
        (catch ArithmeticException e (println "No / by 0" e)) ; Catch first one
        (catch Exception e (println "what" e))
        (finally (println "done"))
        )
    )

(defn myf [n]
    {:pre [(instance? number n) (> n 0)]
     :post [(map %)]}
    {:return n}
    )

(myf "a") -> AssertionError Assert failed

(do (catch Exception ...)
    (myf "a"))

;04/25

catch/throw
AssertionException

(try <expr1> <expr2> ;return <expr2>
    (catch Exception e ...)
    (catch Exception e ...))

catch-clause* -> order from most specific to most general

(try (def myf (slurp "test.txt")) ; non existing file
    (catch java.io.FileNotFoundException e
        (println (.toString e))
        (println (.getMessage e))
        )
    )

;04/27

(partition-all p (range 1 (inc n)))
(defn fact-ref [n p]
    (let [result (ref 1)
          parts (partition-all p (range 1 (inc n)))
          tasks (for [p parts]
                    (future 
                        (Thread/sleep (rand 10))
                        (dosync (alter result (fn [x] (apply *' x p))))
                        ))]
        (dorun (map deref tasks))
        @result
        )
    )

;04/29

Trampoline
(defn factorial-for-tramp [n]
    (letfn [(next-fac-value [limit current-step previous-value]
            (let [next-value (* current-step previous-value)]
                (if (>= limit current-step)
                    next-value
                    #(next-fac-n limit current-step next-value))))
            (next-fac-n [limit previous-step current-value]
                #(next-fac-value limit (inc previous-step) current-value))
            (next-fac-value n 1 1)]
        )
    )

(defn factorial-using-do-dotimes [n]
    (do 
        (def a 1)
        (dotimes [i n]
            (def a (*' a (inc i)))
            )
        )
    a
    )

(defn multi [n]
    (if (> n 0)
        (do 
            (future
                (factorial-using-do-dotimes 10)
                )
            (multi (dec n))
            )
        )
    )

(defn factorial-using-agent [n]
    (let [a (agent 1)]
        (letfn [(calc [current-n limit total]
                    (if (< current-n limit)
                        (let [next-n (inc current-n)]
                            (send-off *agent* calc limit (* total next-n))
                            next-n
                            )
                        total
                        ))]
            )
        (await (send-off a calc n 1))
        )
    @a
    )

(defn timer [n]
    (if (= 0 n)
        (println "Done")
        (do (println "N is now " n)
            (future (Thread/sleep 1000) (timer (dec n)))
            )
        )
    )

;05/06

(defn prime? [x]
    (let [y (range 2 (inc (Math/floor (Math/sqrt x))))]
        (println y)
        (nil? (some #(= (mod x %) 0) y))
        )
    )

(defn integers [x]
    (lazy-seq (cons x (integers (inc x))))
    )

(defmacro myfor [[i n] & body]
    `(let [n# ~n]
        (loop [~i 0]
            (if (< ~i n#)
                (do ~@body (recur (inc ~i)))
                )
            )
        )
    )

(def acc-a (ref 10))
(def acc-b (ref 15))
(defn transfer [acca accb amount]
    (dosync (alter acca - amount) (alter accb + amount))
    )

(re-seq #"\d\.\d{2}\.\d{4}" "4.05.2016")
(macroexpand `(pvalues (deliver z (+ @x @y))
            (do (Thread/sleep 2000) (deliver x 52))
            (do (Thread/sleep 4000) (deliver y 86))))

(pvalues (+ 1 2) (do (Thread/sleep 2000) 100))

(doseq [x [1000 2000 3000]] (Thread/sleep x) (println x))

(defn exercise-agents [send-fn]
  (let [agents (map #(agent %) (range 10))]
       (doseq [a agents]
              (send-fn a (fn [_] (Thread/sleep 1000))))
       (doseq [a agents]
              (await a))))

(try (/ 1 0)
    (catch Exception e (prn "in catch 2" (.getMessage e)))
    (catch ArithmeticException e (prn "in catch 1" (.getMessage e)))
    (finally (prn "in finally")))

(defn factorial-using-agent-recursive [n]
  (let [a (agent 1)]
    (letfn [(calc [current-n limit total]
               (if (< current-n limit)
                 (let [next-n (inc current-n)]
                   (send-off *agent* calc limit (* total next-n))
                   next-n)
                 total))]
      (await (send-off a calc n 1)))
    @a)
  )

(defn fac-using-agent [n]
    (let [a (agent 1)]
        (letfn [(calc [i limit]
                    (if (< i limit)
                        (send-off a * (calc (inc i) limit))
                        )
                    
                    )]
            (calc )
            )
        )
    )