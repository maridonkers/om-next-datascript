(ns browser.parsers.home
  (:require [om.next :as om]
            [datascript.core :as d]

            [browser.reconciler :refer [mutate read]]))

;;-----------------------
;; Parser read functions.
(defmethod read :home/query
  [{:keys [state query]} _ _]

  {:value (d/q '[:find [(pull ?e ?selector) ...]
                 :in $ ?selector
                 :where [?e :home/key]]
               (d/db state) query)})

;;-------------------------
;; Parser mutate functions.
(defmethod mutate 'home/increment
  [{:keys [state]} _ entity]
  
  {:value {:keys [:home/query]}
   :action (fn []
             (d/transact! state
                          [(update-in entity [:home/count] inc)]))})
