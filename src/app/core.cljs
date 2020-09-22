(ns app.core
  (:require [clara.rules :as clara :refer-macros [defrule defsession defquery]]))

(clara/clear-ns-productions!)

(defn fu []
  (prn "bar"))

(defrule is-cold-and-windy-map
  [:temp [{degrees :degrees}] (< degrees 20) (== ?t degrees)]

  =>

  (prn "@@@ notifs/is-cold-and-windy-map")
  ;; uncommenting results in a warning `Use of undeclared Var app.core/fu`
  (fu))

(defn start! []
  (prn "stop"))

(defn stop! []
  (prn "stop"))

(defn main []
  (start!))

(comment
  (test-rules))

(defsession session 'app.core
  :fact-type-fn :type)

(defn test-rules []
  (-> session
      (clara/insert {:type :temp :degrees 15})
      (clara/fire-rules)))
