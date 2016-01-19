(ns browser.parsers.app
  (:require [om.next :as om]
            [datascript.core :as d]

            [browser.reconciler :refer [mutate read]]))

(defmethod read :app/query
  [{:keys [state query]} _ _]

  {:value (d/q '[:find [(pull ?e ?selector) ...]
                 :in $ ?selector
                 :where [?e :app/key]]
               (d/db state) query)})

(defmethod mutate 'app/set-page
  [{:keys [state]} _ entity]

  {:value {:keys [:app/query]}
   :action (fn []
             (d/transact! state
                          [entity]))}) ;; new value in entity

(defmethod mutate 'app/login
  [{:keys [state]} _ entity]

  {:value {:keys [:app/query]}
   :action (fn []
             (d/transact! state
                          [(assoc entity :app/logged-in? true)]))})

(defmethod mutate 'app/logout
  [{:keys [state]} _ entity]

  {:value {:keys [:app/query]}
   :action (fn []
             (d/transact! state
                          [(assoc entity :app/logged-in? false)]))})
