import { Navigate, Route, Routes } from "react-router-dom";
import { LoginPage } from "../../modules/auth/pages/LoginPage";
import { DashboardPage } from "../../modules/dashboard/pages/DashboardPage";
import { hasActiveSession } from "../../modules/auth/services/sessionService";
import { ProtectedRoute } from "./ProtectedRoute";

export function AppRouter() {
  return (
    <Routes>
      <Route
        path="/"
        element={<Navigate to={hasActiveSession() ? "/home" : "/login"} replace />}
      />
      <Route path="/login" element={<LoginPage />} />
      <Route element={<ProtectedRoute />}>
        <Route path="/home" element={<DashboardPage />} />
      </Route>
      <Route path="*" element={<Navigate to="/" replace />} />
    </Routes>
  );
}
