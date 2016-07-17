;sec8-hw6
;Tianhao Liu
;liux3271
;04/19/2016
;===========================================================================
(use 'clojure.java.io)
;===========================================================================
(def log_name "NASA_access_log_Jul95_short")
;===========================================================================
(def IP_address #"^\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3} ")
(def private_IP_address #"(127\.\d{1,3}\.\d{1,3}\.\d{1,3} |10\.\d{1,3}\.\d{1,3}\.\d{1,3} |192\.168\.\d{1,3}\.\d{1,3} |192\.88\.99\.\d{1,3} )")
(def on-date4th-5th #"(04/Jul/1995:\d\d:\d\d:\d\d -\d{4}|05/Jul/1995:\d\d:\d\d:\d\d -\d{4})")
(def req1000-1030pm5thJul #"05/Jul/1995:22:[0-2][0-9]:\d\d -\d{4}")
(def not_successful_req #"^.*\[.*\] \".*\" 5\d\d")
(def redirected_req #"^.*\[.*\] \".*\" 3\d\d")
(def more_than_50000 #"\".*\" \d\d\d (\d{6,}|[5-9]\d{4})")
;===========================================================================
(defn getstr [line reg]
    (first (re-seq reg line))
    )

(defn cnt [filename reg]
    (with-open [rdr (reader filename)]
        (loop [lines (line-seq rdr) c 0]
            (if (empty? lines)
                c
                (let [s (getstr (first lines) reg)]
                    (if (not (nil? s))
                        (recur (rest lines) (inc c))
                        (recur (rest lines) c)))))))

(defn find-hosts [filename reg]
    (with-open [rdr (reader filename)]
        (loop [lines (line-seq rdr) hosts []]
            (if (empty? lines)
                (distinct hosts)
                (let [s (getstr (first lines) reg)]
                    (if (not (nil? s))
                        (recur (rest lines) (conj hosts (first (re-seq #"^[^ ]+" (first lines)))))
                        (recur (rest lines) hosts)))))))

(defn find-req [filename reg]
    (with-open [rdr (reader filename)]
        (loop [lines (line-seq rdr) reqs []]
            (if (empty? lines)
                (seq reqs)
                (let [s (getstr (first lines) reg)]
                    (if (not (nil? s))
                        (recur (rest lines) (conj reqs (first lines)))
                        (recur (rest lines) reqs)))))))
;===========================================================================
(defn count-IP [] (cnt log_name IP_address))
(defn find-private-IP [] (find-req log_name private_IP_address))
(defn count-request-on-dates [] (cnt log_name on-date4th-5th))
(defn count-hosts [] (find-hosts log_name req1000-1030pm5thJul))
(defn find-server-error [] (find-req log_name not_successful_req))
(defn count-redirect [] (cnt log_name redirected_req))
(defn count-morethan50000 [] (cnt log_name more_than_50000))
;===========================================================================

;Bonus questions
;===========================================================================
;The code from https://www.rosettacode.org/wiki/Inverted_index#Clojure
(ns inverted-index.core
  (:require [clojure.set :as sets]
            [clojure.java.io :as io]))
 
(def pattern #"\w+")     ; Java regex for a raw term: here a substring of alphanums
(defn normalize [match] (.toLowerCase match))  ; normalization of a raw term
 
(defn term-seq [text] (map normalize (re-seq pattern text)))
 
(defn set-assoc 
  "Produces map with v added to the set associated with key k in map m"
  [m k v] (assoc m k (conj (get m k #{}) v)))
 
(defn index-file [index file]
  (with-open [reader (io/reader file)]
    (reduce
      (fn [idx term] (set-assoc idx term file))
      index
      (mapcat term-seq (line-seq reader)))))
 
(defn make-index [files]
  (reduce index-file {} files))
 
(defn search [index query]
  (apply sets/intersection (map index (term-seq query))))
;end of the code from https://www.rosettacode.org/wiki/Inverted_index#Clojure
;===========================================================================

(ns user)
(def make-index inverted-index.core/make-index)
