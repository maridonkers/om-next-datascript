(ns browser.app
  (:require [om.next :as om :refer-macros [defui]]
            [om.dom :as dom]
            [secretary.core :as secretary]

            [browser.parsers.app :as app-parser]
            
            [browser.navbar :refer [Navbar navbar]]
            [browser.pages.home :refer [HomePage home-page]]
            [browser.pages.about :refer [AboutPage about-page]]
            [browser.pages.error :refer [ErrorPage error-page]]))

;;------------------
;; Om Next component
;;
;; This defines dimensions.
(defui Dimensions
  static om/IQuery
  (query [this]
         [:db/id
          :dimensions/orientation
          :dimensions/width
          :dimensions/height]))

;;------------------------
;; Om Next root component.
;;
(defui App
  static om/IQuery
  (query [this]
         [{:app/query [:db/id
                       :app/page
                       :app/locale
                       :app/logged-in?
                       {:app/dimensions (om/get-query Dimensions)}]}
          
          {:navbar/query (om/get-query Navbar)}
          {:home/query (om/get-query HomePage)}
          {:about/query (om/get-query AboutPage)}
          {:error/query (om/get-query ErrorPage)}])

  Object
  (render [this]
          (let [props (om/props this)
                
                app-props (get-in (om/props this) [:app/query 0])
                navbar-props (get-in (om/props this) [:navbar/query 0])
                home-props (get-in (om/props this) [:home/query 0])
                about-props (get-in (om/props this) [:about/query 0])
                error-props (get-in (om/props this) [:error/query 0])

                {:keys [db/id
                        app/page
                        app/locale
                        app/logged-in?]} app-props]
            
            (dom/div nil
                     (navbar (om/computed navbar-props
                                          {:app-id id
                                           :lc locale
                                           :logged-in? logged-in?}))

                     ;; Extend this when new pages are added. Also see routes
                     ;; in browser.routing component.
                     ;;
                     (case page
                       "/" (home-page home-props)
                       "/about" (about-page about-props)
                       (error-page error-props))))))
