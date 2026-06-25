import { cn } from "../../lib/utils";

export function Badge({ className, ...props }) {
  return (
    <p
      className={cn(
        "inline-flex items-center gap-2 rounded-md border border-emerald-200 bg-emerald-50 px-3 py-1 text-sm font-medium text-emerald-800",
        className,
      )}
      {...props}
    />
  );
}
