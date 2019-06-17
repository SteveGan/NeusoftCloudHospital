//通过职位id得到需要转跳到的path
export function routeByPositionId(positionId) {
  var path = "";
  switch (positionId) {
    //收费员
    case 1:
      path = "/home/cashier";
      break;
    case 2:
      path = "/home/outpatientdoctor";
      break;
    case 3:
      path = "/home/techdoctor";
      break;
    case 4:
      path = "/home/drugstation";
      break;
    case 5:
      path = "/home/finicialadmin";
      break;
    case 6:
      path = "/home/admin";
      break;
  }
  return path;
}
