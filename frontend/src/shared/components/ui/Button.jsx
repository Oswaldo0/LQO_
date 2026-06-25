import { cn } from "../../lib/utils";

const variants = {
  default: "bg-slate-950 text-white shadow-sm hover:bg-slate-800",
  primary: "bg-indigo-600 text-white shadow-sm hover:bg-indigo-500",
  secondary:
    "border border-slate-300 text-slate-800 hover:border-slate-400 hover:bg-white",
};

export function Button({ className, variant = "default", ...props }) {
  return (
    <button
      className={cn(
        "inline-flex items-center justify-center gap-2 rounded-md px-4 py-2 text-sm font-semibold transition disabled:cursor-not-allowed disabled:opacity-60",
        variants[variant],
        className,
      )}
      {...props}
    />
  );
}
