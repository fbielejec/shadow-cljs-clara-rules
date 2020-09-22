(ns app.core
  (:require [clara.rules :as clara :refer-macros [defrule defsession defquery]]
            [cljs.core.async :refer [<! go]]))

(clara/clear-ns-productions!)

(defn fu []
  (prn "bar"))

;; BEGIN : rules

(defrule is-cold-map
  [:temp [{degrees :degrees}] (< degrees 20) (== ?t degrees)]

  =>
  (go
    (prn "@@@ is-cold")
    ;; uncommenting results in a warning `Use of undeclared Var app.core/fu`
    (fu)

    (clara/insert! {:type :wind :mph 45})))

(defrule is-windy
  [:wind [{mph :mph}] (> mph 30) (== ?w mph)]

  =>

  (prn "@@@ is-windy!"))

;; END : rules

(defn start! []
  (prn "stop"))

(defn stop! []
  (prn "stop"))

(defn main []
  (start!))

(defsession session 'app.core
  :fact-type-fn :type)

(defn test-rules []
  (-> session
      (clara/insert {:type :temp :degrees 15})
      (clara/fire-rules)))

(comment
  (test-rules))
