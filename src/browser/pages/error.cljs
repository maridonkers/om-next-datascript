(ns browser.pages.error
  (:require [om.next :as om :refer-macros [defui]]
            [om.dom :as dom]
            [datascript.core :as d]

            [browser.util :as util]

            [browser.parsers.error :as error-parser]))

;; -------------------------------------
;; Om Next component for the error page.
(defui ErrorPage
  static om/IQuery
  (query [this]
         [:db/id :error/title])
  Object
  (render [this]
          (let [props (om/props this)

                {:keys [error/title]} props]
            
            (dom/div
             nil 
             (dom/h2 nil title)
             (dom/button
              #js {:type "button"
                   :onClick (fn [e]
                              (util/nav! "/"))}
              "HOME!")))))

(def error-page (om/factory ErrorPage))
