(ns clojurelive-web.actions
  (:require [clojurelive-web.data.comment :as comment]
            [clojurelive-web.data.reset-password :as reset-password]
            [clojurelive-web.data.topic :as topic]
            [clojurelive-web.data.user :as user]
            [clojurelive-web.mailer :as mailer]
            [clojurelive-web.view.login :as login-view]
            [clojurelive-web.view.signup :as signup-view]
            [ring.util.response :as ring-response]))

(defn authenticated? [req]
  (not-empty (:clojurelive/username (:session req))))

(defn signup [req]
  (let [params (:params req)
        session (:session req)
        r (user/create params)]
    (if (:errors r)
      (signup-view/show session r)
      (let [session (assoc session :clojurelive/username (:username params))]
        (-> (ring-response/redirect "/")
            (assoc :session session))))))

(defn login [req]
  (let [params (:params req)
        session (:session req)
        authenticated? (user/authenticate (:username params) (:password params))]
    (if authenticated?
      (let [session (assoc session :clojurelive/username (:username params))]
        (-> (ring-response/redirect "/")
            (assoc :session session)))
      (login-view/show session {:errors "invalid username or password"}))))

(defn logout [req]
  (-> (ring-response/redirect "/")
      (assoc :session nil)))

(defn for-topic [req]
  (topic/for-uuid (:uuid (:params req))))

(defn newest-topics []
  (topic/newest 0 25))

(defn new-link [req]
  (if (not (authenticated? req))
    (ring-response/redirect "/login")
    (let [link-uuid (topic/create-link (:clojurelive/username (:session req))
                                       {:title (:title (:params req))
                                        :url (:url (:params req))})]
      (ring-response/redirect (str "/t/" link-uuid) :see-other))))

(defn new-text [req]
  (if (not (authenticated? req))
    (ring-response/redirect "/login")
    (let [text-uuid (topic/create-text (:clojurelive/username (:session req))
                                       {:title (:title (:params req))
                                        :content (:content (:params req))})]
      (ring-response/redirect (str "/t/" text-uuid) :see-other))))

(defn send-reset-password-email [req]
  (let [email (:email (:params req))
        token (reset-password/create email)]
    (when token
      (mailer/reset-password email token))
    (ring-response/redirect "/")))

(defn reset-password [req]
  (reset-password/update (:uuid (:params req)) (:password (:params req)))
  (ring-response/redirect "/login"))

(defn new-comment [req]
  (if (not (authenticated? req))
    (ring-response/redirect "/login")
    (let [comment (comment/create (:clojurelive/username (:session req))
                                  {:parent-uuid (:uuid (:params req))
                                   :body (:body (:params req))})]
      (ring-response/redirect (str "/t/" (:uuid (:params req)))))))
