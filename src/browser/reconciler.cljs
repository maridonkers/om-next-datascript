(ns browser.reconciler
  (:require [om.next :as om]
            
            [browser.ds :as ds]))

;; -------------------------
;; The Om Next read functions
(defmulti read
  "Read data from DataScript store."
  om/dispatch)

;; -----------------------------
;; The Om Next mutate functions.
(defmulti mutate
  "Mutate data in DataScript store."
  om/dispatch)


;; -------------------
;; The Om Next parser.
;;
(def parser (om/parser {:read read :mutate mutate}))

;; -------------------------
;; Configures Om Next read and mutate functions.
(def reconciler
  (om/reconciler
   {:state ds/conn
    :parser parser}))
