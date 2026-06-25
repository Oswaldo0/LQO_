import { cn } from "../../../shared/lib/utils";

export function FeatureItem({ feature, active }) {
  const Icon = feature.icon;

  return (
    <div
      className={cn(
        "flex gap-4 rounded-md border p-4 transition",
        active ? "border-indigo-200 bg-indigo-50" : "border-slate-200 bg-white",
      )}
    >
      <div
        className={cn(
          "grid size-11 shrink-0 place-items-center rounded-md",
          active ? "bg-indigo-600 text-white" : "bg-slate-100 text-slate-700",
        )}
      >
        <Icon className="size-5" />
      </div>
      <div>
        <h4 className="font-bold">{feature.title}</h4>
        <p className="mt-1 text-sm leading-6 text-slate-600">{feature.description}</p>
      </div>
    </div>
  );
}
