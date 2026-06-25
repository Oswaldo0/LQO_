import { httpClient } from "../../../shared/services/httpClient";

export async function login({ username, password }) {
  return httpClient("/api/v1/auth/login", {
    method: "POST",
    body: JSON.stringify({ username, password }),
  });
}
