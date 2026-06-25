import { LogOut, Sparkles } from "lucide-react";
import { useNavigate } from "react-router-dom";
import { clearSession } from "../modules/auth/services/sessionService";
import { Button } from "../shared/components/ui/Button";

export function AppShell({ children }) {
  const navigate = useNavigate();

  const handleLogout = () => {
    clearSession();
    navigate("/login", { replace: true });
  };

  return (
    <main className="min-h-screen bg-[#f7f8fb] text-slate-950">
      <section className="mx-auto flex min-h-screen w-full max-w-7xl flex-col px-6 py-6 lg:px-8">
        <nav className="flex items-center justify-between border-b border-slate-200 pb-5">
          <div className="flex items-center gap-3">
            <div className="grid size-10 place-items-center rounded-md bg-slate-950 text-white">
              <Sparkles className="size-5" />
            </div>
            <div>
              <p className="text-sm font-semibold uppercase tracking-[0.2em] text-slate-500">
                Frontend
              </p>
              <h1 className="text-xl font-bold">LQO</h1>
            </div>
          </div>
          <Button onClick={handleLogout}>
            Salir
            <LogOut className="size-4" />
          </Button>
        </nav>

        {children}
      </section>
    </main>
  );
}
