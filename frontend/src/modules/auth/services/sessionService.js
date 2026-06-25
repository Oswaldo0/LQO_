const ACCESS_TOKEN_KEY = "lqo_access_token";
const TOKEN_EXPIRES_AT_KEY = "lqo_token_expires_at";

export function saveSession(session) {
  localStorage.setItem(ACCESS_TOKEN_KEY, session.accessToken);
  localStorage.setItem(TOKEN_EXPIRES_AT_KEY, session.expiresAt);
}

export function clearSession() {
  localStorage.removeItem(ACCESS_TOKEN_KEY);
  localStorage.removeItem(TOKEN_EXPIRES_AT_KEY);
}

export function hasActiveSession() {
  const token = localStorage.getItem(ACCESS_TOKEN_KEY);
  const expiresAt = localStorage.getItem(TOKEN_EXPIRES_AT_KEY);

  if (!token || !expiresAt) {
    return false;
  }

  return new Date(expiresAt).getTime() > Date.now();
}
