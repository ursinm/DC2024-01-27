using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace RV.Migrations
{
    /// <inheritdoc />
    public partial class RenameColumns : Migration
    {
        /// <inheritdoc />
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.RenameColumn(
                name: "lastName",
                table: "tbl_User",
                newName: "lastname");

            migrationBuilder.RenameColumn(
                name: "firstName",
                table: "tbl_User",
                newName: "firstname");
        }

        /// <inheritdoc />
        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.RenameColumn(
                name: "lastname",
                table: "tbl_User",
                newName: "lastName");

            migrationBuilder.RenameColumn(
                name: "firstname",
                table: "tbl_User",
                newName: "firstName");
        }
    }
}
