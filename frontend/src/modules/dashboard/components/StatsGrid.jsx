export function StatsGrid({ stats }) {
  return (
    <div className="grid gap-3 sm:grid-cols-3">
      {stats.map((stat) => (
        <div key={stat.label} className="rounded-md border border-slate-200 p-4">
          <p className="text-3xl font-black">{stat.value}</p>
          <p className="mt-1 text-sm text-slate-500">{stat.label}</p>
        </div>
      ))}
    </div>
  );
}
