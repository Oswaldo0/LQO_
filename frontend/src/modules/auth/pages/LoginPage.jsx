import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { LogIn, LockKeyhole, User } from "lucide-react";
import { Button } from "../../../shared/components/ui/Button";
import { login } from "../services/authService";
import { saveSession } from "../services/sessionService";

export function LoginPage() {
  const navigate = useNavigate();
  const [form, setForm] = useState({ username: "", password: "" });
  const [status, setStatus] = useState({ type: "idle", message: "" });

  const updateField = (field) => (event) => {
    setForm((current) => ({ ...current, [field]: event.target.value }));
  };

  const handleSubmit = async (event) => {
    event.preventDefault();
    setStatus({ type: "loading", message: "" });

    try {
      const session = await login(form);
      saveSession(session);
      navigate("/home", { replace: true });
    } catch (error) {
      setStatus({
        type: "error",
        message: error.message || "No se pudo iniciar sesion.",
      });
    }
  };

  const isLoading = status.type === "loading";

  return (
    <main className="grid min-h-screen bg-[#f6f7f9] px-4 py-8 text-slate-950">
      <section className="mx-auto grid w-full max-w-md content-center">
        <div className="mb-8">
          <p className="text-sm font-semibold uppercase text-slate-500">LQO</p>
          <h1 className="mt-2 text-3xl font-bold">Ingreso</h1>
        </div>

        <form
          onSubmit={handleSubmit}
          className="space-y-5 rounded-lg border border-slate-200 bg-white p-6 shadow-sm"
        >
          <label className="block">
            <span className="text-sm font-medium text-slate-700">Usuario</span>
            <span className="mt-2 flex items-center gap-3 rounded-md border border-slate-300 bg-white px-3 py-2 focus-within:border-slate-950">
              <User className="size-4 text-slate-500" />
              <input
                className="w-full border-0 bg-transparent text-sm outline-none"
                name="username"
                autoComplete="username"
                value={form.username}
                onChange={updateField("username")}
                required
              />
            </span>
          </label>

          <label className="block">
            <span className="text-sm font-medium text-slate-700">Contrasena</span>
            <span className="mt-2 flex items-center gap-3 rounded-md border border-slate-300 bg-white px-3 py-2 focus-within:border-slate-950">
              <LockKeyhole className="size-4 text-slate-500" />
              <input
                className="w-full border-0 bg-transparent text-sm outline-none"
                name="password"
                type="password"
                autoComplete="current-password"
                value={form.password}
                onChange={updateField("password")}
                required
              />
            </span>
          </label>

          {status.message ? (
            <p
              className={
                status.type === "error"
                  ? "text-sm font-medium text-red-600"
                  : "text-sm font-medium text-emerald-700"
              }
            >
              {status.message}
            </p>
          ) : null}

          <Button className="w-full" type="submit" disabled={isLoading}>
            <LogIn className="size-4" />
            {isLoading ? "Ingresando..." : "Ingresar"}
          </Button>
        </form>
      </section>
    </main>
  );
}
